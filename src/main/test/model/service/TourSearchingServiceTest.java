package ua.den.model.service;

import ua.den.model.entity.dto.Cruise;
import ua.den.model.exception.NoResultException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TourSearchingServiceTest {
    @Mock
    private TourSearchingService tourSearchingService = Mockito.mock(TourSearchingService.class);
    private List<Cruise> cruiseList;

    @Before
    public void setUp() throws Exception {
        cruiseList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Cruise cruise = new Cruise.Builder()
                    .id(22 + i)
                    .name("Cruise" + i)
                    .price(22 + i * 10)
                    .shipId(223 - i).build();

            cruiseList.add(cruise);
        }

        Mockito.when(tourSearchingService.searchCruisesFullInfo()).thenReturn(cruiseList);
    }

    @Test
    public void searchCruisesFullInfo() throws NoResultException {
        assertEquals(cruiseList, tourSearchingService.searchCruisesFullInfo());
    }
}