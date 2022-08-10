
module.exports={
    existenceUser:function (con,req,callback) {
        var email=req.body['email']
        var query="select * from users where u_email='"+email+"'"

        con.query(query,function (err,result,field) {
            if (err) {

                throw err
            }
            if(result.length==0) {
                console.log("dont exist user")
                callback(false)
            }else {
                console.log("exist user")
                callback(true)
            }

        })

    }
}