
module.exports={
    registerUser:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var existenceuser=require("../basefunction/existenceUser")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        existenceuser.existenceUser(con,req,function (returnvalue) {
           if(returnvalue)
           {
               resp.setHeader('Content-Type', 'application/json');
               resp.send({status:false})
           }
           else{
               ///////////
               var sql=`INSERT INTO users (u_email,u_username,u_password,u_description) VALUES ('${req.body['email']}','${req.body['username']}','${req.body['password']}','${req.body['description']}')`


               con.query(sql,function (err,result) {
                   if(err)
                       throw err
                   console.log(result)
               })
               setTimeout(function () {
                    con.query(`select u_id from users where u_email='${req.body['email']}'`,function (err,result,field) {
                        if(err)
                            throw err
                        console.log(result)
                        var request={
                            status:true,
                            id:result[0].u_id
                        }

                        resp.setHeader('Content-Type', 'application/json')
                        resp.send(request)
                    })

               },50)

               //////////
           }
        })


    }
}
