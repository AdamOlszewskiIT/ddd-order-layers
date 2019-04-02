package pl.com.altar.ecommerce.sales.infrastructure.persistence.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationItemData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
@Table(name = "reservation_table")
@AllArgsConstructor
public class ReservationEntity implements ReservationData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private Long id;
    private String statusName;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReservationItemEntity> items;
    @Embedded
    private ClientData clientData;
    private Date createDate;


    public ReservationEntity(ReservationData reservationData) {
        this.id = reservationData.getId();
        this.statusName = reservationData.getStatusName();
        this.items = parse(reservationData.getItemsProjections());
        this.clientData = reservationData.getClientData();
        this.createDate = reservationData.getCreateDate();
    }

    @Override
    public List<ReservationItemData> getItemsProjections() {
        return new ArrayList<>(this.items);
    }

    private List<ReservationItemEntity> parse(List<ReservationItemData> reservationItemData) {
        return reservationItemData.stream()
                .map(item -> new ReservationItemEntity(item, this))
                .collect(Collectors.toList());
    }
}
