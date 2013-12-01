package fr.sg.fmk.domain;

import fr.sg.fmk.constant.Format;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entité représentant un paramètre
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_PARAMETER")
public class Parameter extends GenericDomain {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank
    @Size(min = 10)
    @Column(nullable = false)
    private String label;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Format format;

    @NotNull
    @Column(nullable = false)
    @Digits(integer = 1, fraction = 0)
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (code != null ? !code.equals(parameter.code) : parameter.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Parameter{");
        sb.append("label='").append(label).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
