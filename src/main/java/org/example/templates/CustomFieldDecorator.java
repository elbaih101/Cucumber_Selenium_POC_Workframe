package org.example.templates;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * this Class Extends the custom Field Decorator then can be used inside the BasePom in replace of the Default Field decorator
 * PageFactory.initElements(new CustomFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
 * enabling the user of creating custom FindBys Annotations for The Custom WebElements
 */
public class CustomFieldDecorator extends DefaultFieldDecorator
{
    private static final Logger logger = LoggerFactory.getLogger(CustomFieldDecorator.class);

    public CustomFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    /**
     * @param loader The class loader that was used for the page object
     * @param field  The field that may be decorated.
     * @return decorated Object of the element locater and theClass of custom elment
     */
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (field.isAnnotationPresent(FindBy.class)||field.isAnnotationPresent(FindBys.class)||field.isAnnotationPresent(FindAll.class)) {
            ElementLocator locator = factory.createLocator(field);

            if (locator == null) {
                return null;
            }

            // Handle custom elements
            if (CustomWebElement.class.isAssignableFrom(field.getType())) {
                return CustomProxy(field.getType(), loader, locator);
            }

            // Handle List<CustomWebElement>
            else if (List.class.isAssignableFrom(field.getType())) {
                // Extract the generic type of the List
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                    Class<?> typeClass = (Class<?>) listType;
                    if (CustomWebElement.class.isAssignableFrom(typeClass)) {
                        return CustomListProxy(typeClass, loader, locator);
                    }

                }
            }
        }

        return super.decorate(loader, field);
    }

    /**
     * This method creates a proxy instance of the specified class using the provided WebElement as an argument.
     * It finds a constructor that takes a WebElement as an argument and creates a new instance using that constructor.
     *
     * @param clazz   The class of the proxy instance to be created.
     * @param loader  The class loader to be used for loading the specified class.
     * @param locator The locator that provides the WebElement to be used for creating the proxy instance.
     * @return A new instance of the specified class, initialized with the provided WebElement.
     * @see ElementLocator
     * @see WebElement
     */
    private <T> T CustomProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator) {
        try {
            WebElement proxy = proxyForLocator(loader, locator);

            // Find a constructor that takes a WebElement as an argument
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);

            // Create a new instance using the found constructor
            return constructor.newInstance(proxy);
        } catch (Exception e) {
            // Handle exceptions such as NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create proxy instance", e);
        }
    }

    /**
     * Creates a proxy list of custom elements of type <T>.
     * <p>
     * This method creates a list of proxy instances for custom elements defined by the class `clazz`.
     * Each custom element is instantiated using a constructor that takes a `WebElement` as its argument.
     * The `proxyForListLocator` method is used to obtain a list of `WebElement` proxies.
     *
     * @param <T> The type of the custom elements.
     * @param clazz The `Class` object representing the custom element type.
     * @param loader The `ClassLoader` used to load the class.
     * @param locator The `ElementLocator` used to locate the list of elements.
     * @return A list of custom elements of type `T`.
     * @throws RuntimeException if there is an issue creating instances of the custom element type.
     */
    private <T> List<T> CustomListProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator) {
        try {
            List<WebElement> proxy = proxyForListLocator(loader, locator);
            List<T> customElements = new ArrayList<>();
            // Find a constructor that takes a WebElement as an argument
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);
            for (WebElement element : proxy) {
                customElements.add(constructor.newInstance(element));
            }
            // Create a new instance using the found constructor
            return customElements;
        } catch (Exception e) {
            // Handle exceptions such as NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create proxy instance", e);
        }
    }
}

