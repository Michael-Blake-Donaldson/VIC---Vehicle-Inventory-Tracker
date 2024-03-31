package Vehicle_Inventory_Control;

import java.io.Serializable;

public class Vehicle implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7511510504958610665L;
	private String name;
    private String filingNumber;
    private boolean damaged;
    private String damageReason;
    private String usage;
    private String notes;
    private boolean resolved;

    public Vehicle(String name, String filingNumber, boolean damaged, String damageReason, String usage, String notes, boolean resolved) {
        this.name = name;
        this.filingNumber = filingNumber;
        this.damaged = damaged;
        this.damageReason = damageReason;
        this.usage = usage;
        this.notes = notes;
        this.resolved = resolved;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilingNumber() {
        return filingNumber;
    }

    public void setFilingNumber(String filingNumber) {
        this.filingNumber = filingNumber;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public String getDamageReason() {
        return damageReason;
    }

    public void setDamageReason(String damageReason) {
        this.damageReason = damageReason;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}