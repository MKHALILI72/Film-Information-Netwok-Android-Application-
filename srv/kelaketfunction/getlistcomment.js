module.exports={
    getlistcomment:function (req,resp) {

        var connectdb=require("../basefunction/connectDb")
        var checkuserlogin=require("../basefunction/checkUserLogin")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        checkuserlogin.checkUserLogin(req.body['email'],req.body['password'],function (returnvalue) {
            if(returnvalue==false)
            {
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }
            else{

                var fid=req.body['f_id']
                var query=`SELECT users.u_username,addcomment.a_comment FROM users inner join addcomment on users.u_id=addcomment.u_id where f_id=${req.body['f_id']}`
                console.log(query)
                con.query(query,function (err,result,field) {
                    if (err)
                        throw err
                    if(result.length!=0){
                        var myres=[]
                        for (var i=0;i<result.length;i++) {
                            myres[i]={
                                status:true,
                                username:result[i].u_username,
                                comment:result[i].a_comment
                            }
                        }
                        resp.setHeader('Content-Type', 'application/json');
                        resp.send(myres)
                    }else{
                        console.log("comment not defined")
                        resp.setHeader('Content-Type', 'application/json');
                        resp.send([{status:false}])
                    }
                })
            }

        })
    }
}