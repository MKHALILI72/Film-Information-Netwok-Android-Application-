module.exports={
    login:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var checkUserLogin=require("../basefunction/checkUserLogin")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")

        checkUserLogin.checkUserLogin(req.body['email'],req.body['password'],function (returnvalue) {
            if(returnvalue)
            {
                  var returnuserinfo=require('../basefunction/returnUserInfo')
                      returnuserinfo.returnUserInfo(req,resp)
            }
            else{
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }

        })
    }
}