package rs.ac.uns.ftn.xws.entities.self;

import java.io.FileInputStream;
import java.util.Properties;

public class Company {
    
    
    protected Company() {
        try {
            //properties = new Properties();
            //properties.load(new FileInputStream("company.properties"));
            //name = properties.getProperty("name");
            //url = properties.getProperty("url");
            //TIN = properties.getProperty("TIN");
            name="Weyland-Yutani";
            url="192.168.1.80";
            TIN="tintintinti";
            maxAmount = 100000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Company getInstance() {
        if (instance == null)
            instance = new Company();
        
        return instance;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    
    public String getTIN() {
        return TIN;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    private String name;
    private String url;
    private String TIN;
    private double maxAmount;

    private Properties properties;
    private static Company instance = null;
}
