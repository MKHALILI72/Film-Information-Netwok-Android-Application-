module.exports={
    isfilmscored:function (req,resp,callback) {
        var connectdb=require("../basefunction/connectDb")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!--")

        var sqlquery=`select * from score where u_id=${req.body['u_id']} and f_id=${req.body['f_id']}`
        console.log(sqlquery)
        con.query(sqlquery,function (err,result,field) {
            if(err)
                throw err
            console.log(result)
            if(result.length==0)
                callback(false)
            else
                callback(true)
        })
    }
}