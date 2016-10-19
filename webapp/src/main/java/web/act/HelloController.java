package web.act;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class HelloController {
	
	@RequestMapping(value={"/welcome","/index","/"},method=RequestMethod.GET)
	public String welcome(){
		return "index";
	}
}
