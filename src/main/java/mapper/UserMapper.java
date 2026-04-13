package mapper;

import dto.AddressResponse;
import dto.CompanyResponse;
import dto.UserResponse;
import models.Address;
import models.Company;
import models.Kunde;

public final class UserMapper {

    private UserMapper() {
        // Prevent instantiation
    }

    public static Kunde mapToKunde(UserResponse user) {
        Address address = null;
        if (user.getAddress() != null) {
            AddressResponse addrResp = user.getAddress();
            double lat = addrResp.getGeo() != null ? addrResp.getGeo().getLatitude() : 0;
            double lng = addrResp.getGeo() != null ? addrResp.getGeo().getLongitude() : 0;

            address = new Address(
                    safe(addrResp.getStreet()),
                    safe(addrResp.getSuite()),
                    safe(addrResp.getCity()),
                    safe(addrResp.getZipcode()),
                    lat,
                    lng);
        }

        Company company = null;
        if (user.getCompany() != null) {
            CompanyResponse compResp = user.getCompany();
            company = new Company(
                    safe(compResp.getName()),
                    safe(compResp.getCatchPhrase()),
                    safe(compResp.getBs()));
        }

        return new Kunde(
                user.getId(),
                safe(user.getName()),
                safe(user.getEmail()),
                safe(user.getUsername()),
                safe(user.getPhone()),
                safe(user.getWebsite()),
                address,
                company);
    }

    private static String safe(String value) {
        return value != null ? value : "";
    }
}
