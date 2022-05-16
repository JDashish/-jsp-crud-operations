<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
<script>

    function toast(sms, error) {
        Toastify({
            text: sms,
            duration: 3000,
            newWindow: true,
            close: true,
            gravity: "top", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            stopOnFocus: true, // Prevents dismissing of toast on hover
            style: {
                background: (error) ? "linear-gradient(to right, #00b09b, #96c93d)" : "linear-gradient(to right, red, red)",
            },
            onClick: function () {} // Callback after click
        }).showToast();
    }

</script>

<%
    String msg, type;
    //checking if msg is not null 
    if (session.getAttribute("message") != null) {
        type = (String) session.getAttribute("type");
        msg = (String) session.getAttribute("message");
        session.removeAttribute("type");
        session.removeAttribute("message");

        if (type.equals("error")) {
%>
<script>
    toast("<%= msg%>");
</script>
<%
} else {
%>
<script>
    toast("<%= msg%>",true);
</script>
<%
        }
    }

%>


<script src="js/app.js"></script>


</body>
</html>
