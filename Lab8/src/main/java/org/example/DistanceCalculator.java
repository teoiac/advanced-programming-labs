package org.example;
import java.sql.SQLException;
import java.util.List;

public class DistanceCalculator {
    private CityDAO cityDAO;

    public DistanceCalculator(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public DistanceCalculator() {}

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public double calculateDistance(String city1, String city2) throws SQLException
    {
        List<City> cities1 = cityDAO.findByName(city1);
        List<City> cities2 = cityDAO.findByName(city2);

        if(cities1.isEmpty()){
            System.out.println("No cities found" + city1);
            return  Double.MAX_VALUE;
        }

        if(cities2.isEmpty()){
            System.out.println("No cities found" + city2);
            return   Double.MAX_VALUE;
        }

        City firstCity = cities1.get(0);
        City secondCity = cities2.get(0);

        double lat1 = firstCity.getLatitude();
        double lon1 = firstCity.getLongitude();
        double lat2 = secondCity.getLatitude();
        double lon2 = secondCity.getLongitude();

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) +  Math.cos(lat1) * Math.cos(lat2) *  Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;

        return(c * r);
    }


}
