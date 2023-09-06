package com.mmarkhain.blockchain.model;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import sun.security.provider.DSAPublicKeyImpl;

public class Block implements Serializable {
    private byte[] prevHash;
    private byte[] currHash;
    private byte[] minedBy;
    private String timeStamp;
    private Integer ledgerId = 1;
    private Integer miningPoints = 0;
    private Double luck = 0.0;

    private ArrayList<Transaction> transactionLedger = new ArrayList<>();

    //This constructor is used when we retrieve it
    //from the db
    public Block(byte[] prevHash, byte[] currHash, byte[] minedBy, String timeStamp, Integer ledgerId, Integer miningPoints, Double luck) {
        this.prevHash = prevHash;
        this.currHash = currHash;
        this.minedBy = minedBy;
        this.timeStamp = timeStamp;
        this.ledgerId = ledgerId;
        this.miningPoints = miningPoints;
        this.luck = luck;
    }

    //This constructor is used when we initiate it after retrieve.
    public Block(LinkedList<Block> currentBlockChain) {
        var last = currentBlockChain.getLast();
        prevHash = last.getCurrHash();
        ledgerId = last.getLedgerId();
        luck = Math.random() * 1000000;
    }

    public Boolean isVerified(Signature signing) throws SignatureException, InvalidKeyException {
        signing.initVerify(new DSAPublicKeyImpl(this.minedBy));
        signing.update(this.toString().getBytes());
        return signing.verify(this.currHash);
    }

    //This constructor is used only for creating the first
    public Block() {
        prevHash = new byte[]{0};
    }

    public byte[] getPrevHash() {
        return prevHash;
    }

    public byte[] getCurrHash() {
        return currHash;
    }

    public byte[] getMinedBy() {
        return minedBy;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Integer getLedgerId() {
        return ledgerId;
    }

    public Integer getMiningPoints() {
        return miningPoints;
    }

    public Double getLuck() {
        return luck;
    }

    public ArrayList<Transaction> getTransactionLedger() {
        return transactionLedger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Arrays.equals(prevHash, block.prevHash);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(prevHash);
    }

    @Override
    public String toString() {
        return "Block{" +
                "prevHash=" + Arrays.toString(prevHash) +
                ", minedBy=" + Arrays.toString(minedBy) +
                ", timeStamp='" + timeStamp + '\'' +
                ", ledgerId=" + ledgerId +
                ", miningPoints=" + miningPoints +
                ", luck=" + luck +
                '}';
    }
}
