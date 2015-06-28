package test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rs.ac.uns.ftn.xws.entities.self.Company;

public class CompanyTest {

	@BeforeClass
	public static void clear() {
        // CLEAR THE SCREEN
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        // CLEAR THE SCREEN ESCAPE SEQUENCE. May now work on Windows
	}

    @Before
    public void setUp() {
    }

    @Test
    public void loadCompany() {
        Company company = Company.getInstance();
        Assert.assertNotNull(company.getName());
        Assert.assertNotNull(company.getUrl());
        Assert.assertNotNull(company.getTIN());
        System.out.println(company.getName());
    }
}
