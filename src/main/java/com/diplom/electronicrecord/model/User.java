package com.diplom.electronicrecord.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Column(nullable = false)
    private LongProperty id = new SimpleLongProperty();

    @NotNull
    @Size(min = 1,max = 255)
    private StringProperty name = new SimpleStringProperty();

    @NotNull
    @Size(min = 1,max = 255)
    private StringProperty surname = new SimpleStringProperty();

    @NotNull
    @Size(min = 1,max = 255)
    private StringProperty patronymic = new SimpleStringProperty();

    @NotNull(message = "Логин не должен быть пустым")
    @Size(min = 1,max = 255,message = "Логин должен быть до 100 символов")
    private StringProperty login = new SimpleStringProperty();

    @NotNull(message = "Пароль не должен быть пустым")
    @Size(min = 1,max = 255,message = "Пароль должен быть до 100 символов")
    private StringProperty password = new SimpleStringProperty();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id.get();
    }

    public User() {
    }


    private StringProperty fullName = new SimpleStringProperty();

    @Transient
    public String getFullName(){
        return fullName.get();
    }

    public void setFullName(String fullName){
        this.fullName.set(fullName);
    }


    public void setId(Long id) {
        this.id.set(id);
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.get() == user.id.get() &&
                login.get().equals(user.getLogin()) &&
                password.get().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.get(),login.get(),password.get());
    }
}
