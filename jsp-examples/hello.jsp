<html>
   <head>
   		<title>Hello World</title>
   	</head>
   
   <body>
      Hello World!<br/>

    <!-- Hello world example -->
    <% out.println("Your IP address is " + request.getRemoteAddr()); %>

    <!-- Lifecycle states -->
  	<%! public void jspInit() { System.out.println("Initialization - jspInit"); } %>
  	<% System.out.println("Execution"); %>
  	<%! public void jspDestroy() { System.out.println("Cleanup - jspDestroy"); } %>

   </body>
</html>