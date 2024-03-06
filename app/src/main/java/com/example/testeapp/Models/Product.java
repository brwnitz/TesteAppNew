package com.example.testeapp.Models;

public class Product {
    String nome;
    String imageUrl;
    Long price;
    int quantity;
    int id;
    public Product(String nome, String imageUrl, Long price) {
        this.nome = nome;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){return this.quantity;}

}
