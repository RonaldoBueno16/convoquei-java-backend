package br.com.convoquei.backend.organization.model.dto.request;

import br.com.convoquei.backend._shared.model.dto.request.PagedRequest;
import jakarta.validation.constraints.Size;

public class ListOrganizationsByMemberRequest extends PagedRequest {

    @Size(min = 3, message = "name deve conter no mínimo 3 caracteres")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}
