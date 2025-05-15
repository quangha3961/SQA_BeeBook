package beebooks.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class BaseSearchModel {

	protected int page;
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
