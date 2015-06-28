package rs.ac.uns.ftn.xws.entities.self;

import java.io.FileInputStream;
import java.util.Properties;

public class Company {
    
    
    protected Company() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("company.properties"));
            name = properties.getProperty("name");
            url = properties.getProperty("url");
            TIN = properties.getProperty("TIN");
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

    private String name;
    private String url;
    private String TIN;

    private Properties properties;
    private static Company instance = null;
}
