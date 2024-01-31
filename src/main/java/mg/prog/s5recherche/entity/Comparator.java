package mg.prog.s5recherche.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comparator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "parameter_type", nullable = false)
    private String parameterType = "numeric";

    @Column(name = "parameter_number", nullable = false)
    private Integer parameterNumber = 1;


    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Comparator name must not be empty");
        }
        this.name = name;
    }

    public void setParameterType(String parameterType) {
        if (parameterType == null || parameterType.isEmpty()) {
            throw new IllegalArgumentException("Comparator parameter type must not be empty");
        }
        if (!parameterType.equals("numeric") && !parameterType.equals("boolean") && !parameterType.equals("string")) {
            throw new IllegalArgumentException("Comparator parameter type must be numeric, boolean or string");
        }
        this.parameterType = parameterType;
    }

    public void setParameterNumber(Integer parameterNumber) {
        if (parameterNumber == null) {
            throw new IllegalArgumentException("Comparator parameter number must not be empty");
        }
        if (parameterNumber < 1) {
            throw new IllegalArgumentException("Comparator parameter number must be greater than 0");
        }
        this.parameterNumber = parameterNumber;
    }

    @Override
    public String toString() {
        return "Comparator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", parameterNumber=" + parameterNumber +
                '}';
    }
}
