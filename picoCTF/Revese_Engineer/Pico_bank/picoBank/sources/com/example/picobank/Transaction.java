package com.example.picobank;

/* loaded from: classes3.dex */
public class Transaction {
    private String amount;
    private String date;
    private boolean incoming;
    private String name;

    public Transaction(String name, String date, String amount, boolean incoming) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.incoming = incoming;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getAmount() {
        return this.amount;
    }

    public boolean isIncoming() {
        return this.incoming;
    }
}
