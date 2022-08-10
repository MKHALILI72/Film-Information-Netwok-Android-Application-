module.exports={
    editeuserinfo:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var checkUserLogin=require("../basefunction/checkUserLogin")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!!")

        checkUserLogin.checkUserLogin(req.body['email'],req.body['password'],function (returnvalue) {
            if(returnvalue)
            {
                var query=`update users set u_username='${req.body['username']}',u_password='${req.body['npassword']}',u_description='${req.body['description']}',u_profilepic='${req.body['profilepic']}' where u_email='${req.body['email']}'`
                console.log(query)
                con.query(query,function (err,result,field) {
                    if(err)
                        throw err
                    console.log("user not defined")
                    resp.setHeader('Content-Type', 'application/json');
                    resp.send({status:true})

                })
            }
            else{
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }

        })
        
    }
}