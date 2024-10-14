//package com.neeraj.library_mmgt_system.model;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_category_id") // This assumes a parent-child relationship
//    private Category parentCategory;
//
//    private int level;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Category getParentCategory() {
//        return parentCategory;
//    }
//
//    public void setParentCategory(Category parentCategory) {
//        this.parentCategory = parentCategory;
//    }
//
//    public int getLevel() {
//        return level;
//    }
//
//    public void setLevel(int level) {
//        this.level = level;
//    }
//}
