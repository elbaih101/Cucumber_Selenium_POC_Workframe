package org.example.pojos;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.tools.CustomDateTimeDeserializer;
import org.example.tools.CustomDateTimeSerializer;
import org.example.tools.JsonUtils;
import org.joda.time.DateTime;

/**
 * tax pojo used for financial transactions and calculations
 */
public class Some_Pojo_As_Test_Data_Related
{
    private static final String jsonPath = "src/main/resources/testdata/testDataPojo.json";

    private String name;
    private Double age;
    private Double value;
    private String sex;




    public Some_Pojo_As_Test_Data_Related()
    {
    }

    public Some_Pojo_As_Test_Data_Related(String name, Double amount, Double value, String sex)
    {
        setName(name);
        setAge(amount);
        setValue(value);
        setSex(sex);

    }

    public Some_Pojo_As_Test_Data_Related(String name, Double amount, String sex)
    {
        this.name = name;
        this.age = amount;
        this.sex = sex;

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getAge()
    {
        return age;
    }

    public void setAge(double age)
    {
        this.age = age;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }


    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }



    public static Some_Pojo_As_Test_Data_Related getPojoJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new CustomDateTimeSerializer("dd/MM/yyyy"))
                .registerTypeAdapter(DateTime.class, new CustomDateTimeDeserializer("dd/MM/yyyy"))
                .create();
        return JsonUtils.readJsonFromFile(jsonPath, Some_Pojo_As_Test_Data_Related.class, gson);
    }


    /**
     * saves the current instance in the json file path overwriting the previous one
     */
    public void savePojoJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new CustomDateTimeSerializer("dd/MM/yyyy"))
                .registerTypeAdapter(DateTime.class, new CustomDateTimeDeserializer("dd/MM/yyyy"))
                .setPrettyPrinting()
                .create();
        JsonUtils.writeObjectToJsonFile(jsonPath, this, gson);
    }


}
