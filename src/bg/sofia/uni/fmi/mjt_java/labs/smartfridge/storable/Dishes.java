package bg.sofia.uni.fmi.mjt.smartfridge.storable;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public class Dishes implements Storable{

    private LocalDate date;
    private String name;
    private StorableType type;

    public Dishes(String name, LocalDate date) {
        this.name = name;
        this.type = StorableType.OTHER;
        this.date = date;
    }
    public StorableType getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public LocalDate getExpiration(){
        return date;
    }

    public boolean isExpired() {
        return (date.isBefore(LocalDate.now()));
    }
}
