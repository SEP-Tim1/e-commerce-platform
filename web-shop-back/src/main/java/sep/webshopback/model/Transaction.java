package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long purchaseId;

    @Column
    private String status;

    @Column
    private long invoiceId;

    @Column
    private String message;

    public Transaction(long purchaseId, String status, long invoiceId, String message) {
        this.purchaseId = purchaseId;
        this.status = status;
        this.invoiceId = invoiceId;
        this.message = message;
    }
}
