module.exports={
    isaddedfavorite:function (req,resp,callback) {
        var connectdb=require("./connectDb")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        var query=`select * from addfavorite where f_id='${req.body['f_id']}' and u_id='${req.body['u_id']}'`
        con.query(query,function (err,result,field) {
            if(err)
                throw err
            if(result.length!=0)
                callback(true);
            else
                callback(false);
        })
        
    }
}