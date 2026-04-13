package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import Api.ApiImporter;
import models.Kunde;

public class ApiImporterTest {

    private static final String SAMPLE_JSON = "[{\"id\": 1, \"name\": \"Leanne Graham\", \"username\": \"Bret\", \"email\": \"Sincere@april.biz\", \"phone\": \"1-770-736-8031\", \"website\": \"hildegard.org\", \"address\": {\"street\": \"Kulas Light\", \"suite\": \"Apt. 556\", \"city\": \"Gwenborough\", \"zipcode\": \"92998-3874\", \"geo\": {\"lat\": \"-37.3159\", \"lng\": \"81.1496\"}}, \"company\": {\"name\": \"Romaguera-Crona\", \"catchPhrase\": \"Multi-layered client-server neural-net\", \"bs\": \"harness real-time e-markets\"}}, "
            + "{\"id\": 2, \"name\": \"Ervin Howell\", \"username\": \"Antonette\", \"email\": \"Shanna@melissa.tv\", \"phone\": \"010-692-6593\", \"website\": \"anastasia.net\", \"address\": {\"street\": \"Victor Plains\", \"suite\": \"Suite 879\", \"city\": \"Wisokyburgh\", \"zipcode\": \"90566-7771\", \"geo\": {\"lat\": \"-43.9509\", \"lng\": \"-34.4618\"}}, \"company\": {\"name\": \"Deckow-Crist\", \"catchPhrase\": \"Proactive didactic contingency\", \"bs\": \"synergize scalable supply-chains\"}}, "
            + "{\"id\": 3, \"name\": \"Clementine Bauch\", \"username\": \"Samantha\", \"email\": \"Nathan@yesenia.net\", \"phone\": \"1-463-123-4447\", \"website\": \"ramiro.info\", \"address\": {\"street\": \"Douglas Extension\", \"suite\": \"Suite 847\", \"city\": \"McKenziehaven\", \"zipcode\": \"59590-4157\", \"geo\": {\"lat\": \"-68.6102\", \"lng\": \"-47.0653\"}}, \"company\": {\"name\": \"Romaguera-Jacobson\", \"catchPhrase\": \"Face to face bifurcated interface\", \"bs\": \"e-enable strategic applications\"}}]";

    @Test
    public void testParseKundenFromJson() {
        List<Kunde> kunden = ApiImporter.parseKundenFromJson(SAMPLE_JSON);
        Assert.assertNotNull(kunden);
        Assert.assertEquals(3, kunden.size());
    }

    @Test
    public void testParseKundenFromJsonFirstKunde() {
        List<Kunde> kunden = ApiImporter.parseKundenFromJson(SAMPLE_JSON);
        Assert.assertEquals(1, kunden.get(0).getId());
        Assert.assertEquals("Leanne Graham", kunden.get(0).getName());
        Assert.assertEquals("Sincere@april.biz", kunden.get(0).getEmail());
        Assert.assertEquals("Bret", kunden.get(0).getUsername());
        Assert.assertEquals("1-770-736-8031", kunden.get(0).getPhone());
        Assert.assertEquals("hildegard.org", kunden.get(0).getWebsite());
        Assert.assertNotNull(kunden.get(0).getAddress());
        Assert.assertNotNull(kunden.get(0).getCompany());
    }

    @Test
    public void testParseKundenAddressData() {
        List<Kunde> kunden = ApiImporter.parseKundenFromJson(SAMPLE_JSON);
        Assert.assertEquals("Kulas Light", kunden.get(0).getAddress().getStreet());
        Assert.assertEquals("Gwenborough", kunden.get(0).getAddress().getCity());
        Assert.assertEquals("92998-3874", kunden.get(0).getAddress().getZipcode());
        Assert.assertEquals(-37.3159, kunden.get(0).getAddress().getLatitude(), 0.001);
    }

    @Test
    public void testParseKundenCompanyData() {
        List<Kunde> kunden = ApiImporter.parseKundenFromJson(SAMPLE_JSON);
        Assert.assertEquals("Romaguera-Crona", kunden.get(0).getCompany().getName());
        Assert.assertEquals("Multi-layered client-server neural-net", kunden.get(0).getCompany().getCatchPhrase());
    }

    @Test
    public void testParseEmptyJson() {
        List<Kunde> kunden = ApiImporter.parseKundenFromJson("[]");
        Assert.assertNotNull(kunden);
        Assert.assertEquals(0, kunden.size());
    }

    @Test
    public void testParseInvalidJsonReturnsEmpty() {
        List<Kunde> kunden = ApiImporter.parseKundenFromJson("{invalid json");
        Assert.assertNotNull(kunden);
        Assert.assertEquals(0, kunden.size());
    }
}
