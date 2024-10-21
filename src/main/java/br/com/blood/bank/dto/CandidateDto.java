package br.com.blood.bank.dto;

import br.com.blood.bank.domain.Address;
import br.com.blood.bank.domain.Contact;
import br.com.blood.bank.domain.MedicalInformation;
import br.com.blood.bank.domain.Person;
import br.com.blood.bank.utils.StringUtils;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CandidateDto(
        @NotBlank(message = "Nome não pode estar em branco")
        String nome,

        @NotBlank(message = "CPF não pode estar em branco")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve seguir o formato XXX.XXX.XXX-XX")
        String cpf,

        @NotBlank(message = "RG não pode estar em branco")
        @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}", message = "RG deve seguir o formato XX.XXX.XXX-X")
        String rg,

        @NotBlank(message = "Data de nascimento não pode estar em branco")
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Data de nascimento deve seguir o formato DD/MM/YYYY")
        String data_nasc,

        @NotBlank(message = "Sexo não pode estar em branco")
        @Pattern(regexp = "Feminino|Masculino", message = "Sexo deve ser 'Feminino' ou 'Masculino'")
        String sexo,

        @NotBlank(message = "Nome da mãe não pode estar em branco")
        String mae,

        @NotBlank(message = "Nome do pai não pode estar em branco")
        String pai,

        @NotBlank(message = "Email não pode estar em branco")
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank(message = "CEP não pode estar em branco")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve seguir o formato XXXXX-XXX")
        String cep,

        @NotBlank(message = "Endereço não pode estar em branco")
        String endereco,

        @Min(value = 1, message = "Número deve ser maior que 0")
        int numero,

        @NotBlank(message = "Bairro não pode estar em branco")
        String bairro,

        @NotBlank(message = "Cidade não pode estar em branco")
        String cidade,

        @NotBlank(message = "Estado não pode estar em branco")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 letras")
        String estado,

        @NotBlank(message = "Telefone fixo não pode estar em branco")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4}-\\d{4}", message = "Telefone fixo deve seguir o formato (XX) XXXX-XXXX")
        String telefone_fixo,

        @NotBlank(message = "Celular não pode estar em branco")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Celular deve seguir o formato (XX) XXXXX-XXXX")
        String celular,

        @Positive(message = "Altura deve ser positiva")
        double altura,

        @Positive(message = "Peso deve ser positivo")
        double peso,

        @NotBlank(message = "Tipo sanguíneo não pode estar em branco")
        @Pattern(regexp = "A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-", message = "Tipo sanguíneo inválido")
        String tipo_sanguineo
) {
    public Person toPerson(){
        return Person.builder()
                .cpf(cpf)
                .rg(rg)
                .name(nome)
                .fatherName(pai)
                .motherName(mae)
                .email(email)
                .gender(sexo)
                .birth(StringUtils.convertStringToLocalDate(data_nasc))
                .build();
    }

        public Contact toContact() {
                return Contact.builder()
                        .homePhone(telefone_fixo)
                        .cel(celular)
                        .build();
        }


        public Address toAddress() {
                return Address.builder()
                        .zipNumber(cep)
                        .address(endereco)
                        .number(numero)
                        .neighborhood(bairro)
                        .city(cidade)
                        .state(estado)
                        .build();
        }


        public MedicalInformation toMedicalInformation() {
                return MedicalInformation.builder()
                        .height(BigDecimal.valueOf(altura))
                        .weight(BigDecimal.valueOf(peso))
                        .bloodType(tipo_sanguineo)
                        .build();
        }


}

