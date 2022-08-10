module.exports={
    returnUserInfo:function (req,resp) {
        var connectdb=require("./connectDb")
        var con=connectdb.con()
        con.connect()
        console.log("connected")
        var email=req.body['email']
        var password=req.body['password']
        con.query(`select * from users where u_email='${email}' and u_password='${password}'`,function (err,result,field) {
            if(err)
                throw err
            var myresult= {
                status: true,
                id: result[0].u_id,
                email: result[0].u_email,
                username: result[0].u_username,
                password: result[0].u_password,
                description: result[0].u_description
            }
            resp.setHeader('Content-Type', 'application/json')
            resp.send(myresult)

        })

    }




}