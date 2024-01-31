package mg.prog.s5recherche.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Adjective {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "effect", nullable = false)
    private String effect; // + or -

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Adjective name must not be empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Adjective name must not be longer than 50 characters");
        }
        this.name = name.toLowerCase();
    }

    public void setEffect(String effect) {
        if (effect == null || effect.isEmpty()) {
            throw new IllegalArgumentException("Adjective effect must not be empty");
        }
        if (!effect.equals("+") && !effect.equals("-")) {
            throw new IllegalArgumentException("Adjective effect must be + or -");
        }
        this.effect = effect;
    }

    @Override
    public String toString() {
        return "Adjective{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }
}
