module.exports={
    addcomment:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var returnUserId=require("../basefunction/returnUserId")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        returnUserId.returnuserid(con,req,function (returnvalue) {
            if(returnvalue==false)
            {
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }
            else{
                var userid=returnvalue[0].u_id
                var query=`INSERT INTO addcomment (f_id,u_id,a_comment) VALUES (${req.body['f_id']},${userid},'${req.body['comment']}')`
                console.log(query)
                con.query(query,function (err,result,field) {
                    if(err)
                        throw err
                    resp.setHeader('Content-Type', 'application/json');
                    resp.send({status:true})

                })
            }

        })
    }
}