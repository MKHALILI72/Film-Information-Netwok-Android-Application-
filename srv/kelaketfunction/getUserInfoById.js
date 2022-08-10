module.exports={
    getuserinfobyid:function (req,resp) {

        var connectdb=require("../basefunction/connectDb")
        var existenceuser=require("../basefunction/existenceUser")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        existenceuser.existenceUser(con,req,function (returnvalue) {
            if(returnvalue){
            var query=`select u_username,u_description,u_profilepic from users where u_id=${req.body['u_id']}`
                con.query(query,function (err,result,field) {
                    if(err)
                        throw err
                    var response={
                        status:true,
                        username:result[0].u_username,
                        description:result[0].u_description,
                        profilepic:result[0].u_profilepic
                    }
                    console.log("complited")
                    resp.setHeader('Content-Type', 'application/json');
                    resp.send(response)
                })
            }else{
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }




        })

    }
}