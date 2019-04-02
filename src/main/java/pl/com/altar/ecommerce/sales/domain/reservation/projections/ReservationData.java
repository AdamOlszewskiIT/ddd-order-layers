package pl.com.altar.ecommerce.sales.domain.reservation.projections;

import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;

import java.util.Date;
import java.util.List;

public interface ReservationData {
    Long getId();
    String getStatusName();
    List<ReservationItemData> getItemsProjections();
    ClientData getClientData();
    Date getCreateDate();
}
