package com.springai.controller;

import com.springai.Springai2Application;
import com.springai.model.Question;

import jakarta.servlet.http.HttpSession;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
private final ChatClient chatClient;

public HomeController(ChatClient.Builder chatClient) {

	this.chatClient = chatClient.build();
}

@GetMapping
public String show(HttpSession session,ModelMap model)
{
	String s1= """
			Output ONLY a valid raw JSON array. Do not include any conversational text, Markdown formatting, intro or outro.

	Generate 5 random Java True/False declarative statements.

	CRITICAL: Do NOT generate questions starting with "what", "why", "how", "which", or "Is". Every item must be a fact-based statement.

	Each JSON object must have exactly these three keys:
	1. "qno" (integer; sequential number 1 to 5)
	2. "question" (the statement string)
	3. "ans" (boolean: true/false)

	Strict Format Example:

	[
	  {
	    "qno": 1
	    "question": "An interface in Java can have private methods since Java 9.",
	    "ans": true
	  }
	]
	Always return the complete JSON array and end the response with ]
	""";
	
	String s2=chatClient.prompt(s1).call().content();
	ObjectMapper mapper=new ObjectMapper();
	List<Question> list=mapper.readValue(s2, new TypeReference<List<Question>>(){});
	model.addAttribute("cq", list.get(0));
session.setAttribute("list", list);
session.setAttribute("count", 0);
	
	return "question";
}
@GetMapping("/next")
public String next(ModelMap model,HttpSession session,@RequestParam("userans") boolean userans)
{
	List<Question> list=(List<Question>)session.getAttribute("list");
	int count=(int)session.getAttribute("count");
	list.get(count).setUserAns(userans);
	if(userans==list.get(count).isAns())
	{
		list.get(count).setMarks(1);
	}
	if(count==4)
	{
		int c=0;
		for(Question i:list)
		{
			c=c+i.getMarks();
		}	
		model.addAttribute("total",c);
		return "result";
	}
	session.setAttribute("count", ++count);
	model.addAttribute("cq", list.get(count));
	return "question";
}
}
