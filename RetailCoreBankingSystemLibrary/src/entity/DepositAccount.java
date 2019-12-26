package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.DepositAccountType;

@Entity
public class DepositAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;
    @Column(nullable = false)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private DepositAccountType accountType;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal availableBalance;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal holdBalance;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal ledgerBalance;
    @Column(nullable = false)
    private Boolean enabled;
    
    //@OneToMany(mappedBy = "depositAccount")
    //private List<DepositAccountTransaction> transactions;
    @ManyToOne
    private AtmCard atmCard;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    public DepositAccount() {
    }
    
    /*public DepositAccount() {
        transactions = new ArrayList<>();
    }*/

    public DepositAccount(String accountNumber, DepositAccountType accountType, 
                        BigDecimal ledgerBalance, Customer customer) 
    {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.ledgerBalance = ledgerBalance;
        setAvailableBalance(ledgerBalance);
        setHoldBalance(BigDecimal.ZERO);
        setEnabled(true);
        setCustomer(customer);
    }
    
    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountId != null ? depositAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepositAccount)) {
            return false;
        }
        DepositAccount other = (DepositAccount) object;
        if ((this.depositAccountId == null && other.depositAccountId != null) || (this.depositAccountId != null && !this.depositAccountId.equals(other.depositAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccount[ id=" + depositAccountId + " ]";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DepositAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(DepositAccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /*public List<DepositAccountTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DepositAccountTransaction> transactions) {
        this.transactions = transactions;
    }*/

    public AtmCard getAtmCard() {
        return atmCard;
    }

    public void setAtmCard(AtmCard atmCard) {
        this.atmCard = atmCard;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
