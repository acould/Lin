package star.webtool.component;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import star.facade.ecmanager.purview.PurviewFacade;

public class AuthDirective implements TemplateDirectiveModel {

	/**
	 * @param args
	 */
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private PurviewFacade purviewFacade;
	
	@SuppressWarnings("all")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Object resource = params.get("res");
		if (resource!=null &&getAuthBy(resource.toString())) {
			if (body != null) {
				body.render(env.getOut());
			}
		}
	}

	// 获取权限
	private boolean getAuthBy(String resource) {
		
		Long userId = loginComponent.getLoginUserId();		
		return purviewFacade.getAuthByResource(userId,resource);
	}
}
