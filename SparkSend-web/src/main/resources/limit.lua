--KEYS[1]: 限流 key
--ARGV[1]: 限流窗口,毫秒
--ARGV[2]: 当前时间戳（作为score）
--ARGV[3]: 阈值 -> 次数
--ARGV[4]: score 对应的唯一value
-- 1\. 移除开始时间窗口之前的数据（以五分钟为限流窗口，则移除该有序集key在存入redis到此时的五分钟前，这段时间的所有成员）
redis.call("zremrangeByScore", KEYS[1], 0, ARGV[2] - ARGV[1])
-- 2\. 统计当前元素数量
local res = redis.call("zcard", KEYS[1])
-- 3\. 是否超过阈值（若不超过阈值则添加该条记录，并设定过期时间；若超过则返回1表示已经超过阈值）
if (res == nil) or (res < tonumber(ARGV[3])) then
    redis.call("zadd", KEYS[1], ARGV[2], ARGV[4])
    redis.call("expire", KEYS[1], ARGV[1])
    return 0
else
    return 1
end

--redis.call("xxx",KEYS[i],ARGV[i])：call表示执行里面的语句，“xxx”为redis命令，keys表示redis中的key值，argv表示lua脚本所要传入的其他参数

--Redis Zremrangebyscore 命令用于移除有序集中，指定分数（score）区间内的所有成员。
--    基本语法：ZREMRANGEBYSCORE key min max
--    例：ZREMRANGEBYSCORE salary 1500 3500      # 移除所有薪水在 1500 到 3500 内的员工

--Redis Zcard 命令用于计算集合中元素的数量。
--    ZCARD KEY_NAME

--Redis Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中。
--    ZADD KEY_NAME SCORE1 VALUE1.. SCOREN VALUEN

--Redis Expire 命令用于设置 key 的过期时间，key 过期后将不再可用。单位以秒计。（故在上述lua脚本中需要/1000）

--tonumber()函数会尝试将它的参数转换为数字。
--如果参数已经是一个数字或者是一个可以转换成数字的字符串，那么这个函数就会返回转换后的数值，否则，返回nil（表示转换失败）。