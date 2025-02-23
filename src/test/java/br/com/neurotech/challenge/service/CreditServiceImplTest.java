package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.Client;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.impl.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditServiceImplTest {

    private CreditServiceImpl creditService;

    private Client client;

    @BeforeEach
    void setUp() {
        creditService = new CreditServiceImpl();
        client = new Client();
    }

    @Test
    void testCheckCredit_HatchEligible() {
        client.setIncome(10000.0);

        boolean result = creditService.checkCredit(client, VehicleModel.HATCH);

        assertTrue(result);
    }

    @Test
    void testCheckCredit_HatchNotEligible() {
        client.setIncome(3000.0);

        boolean result = creditService.checkCredit(client, VehicleModel.HATCH);

        assertFalse(result);
    }

    @Test
    void testCheckCredit_SUVEligible() {
        client.setIncome(12000.0);
        client.setAge(25);

        boolean result = creditService.checkCredit(client, VehicleModel.SUV);

        assertTrue(result);
    }

    @Test
    void testCheckCredit_SUVNotEligible() {
        client.setIncome(12000.0);
        client.setAge(18);

        boolean result = creditService.checkCredit(client, VehicleModel.SUV);

        assertFalse(result);
    }

    @Test
    void testCheckCredit_NullClient() {
        boolean result = creditService.checkCredit(null, VehicleModel.HATCH);
        assertFalse(result);
    }
}