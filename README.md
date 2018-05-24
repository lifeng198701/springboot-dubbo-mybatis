# springboot-dubbo-mybatis
基于springboot-dubbo-mybatis构架一个通用的开发开发框架</br>
1:模块介绍:</br>
 * a:commons模块主要是一个公共的模块包含内容,在其他任何模块都可以直接引用,此模块需要要求跟任何其他模块都不耦合</br>
       --cache.redis:基于jedis分片客户端</br>
       --exception:任何其他模块异常类的定义</br>
       --json:基于jackson对应json的相互转化的工具类</br>
       --util:一些常用工具类</br>
       --web:web模块下一些公共的类,例如baseController,响应对象的封装</br>
 * b:service模块主要是定义dubbo的接口以及实现两个子模块</br>
       --service-api:主要定义公共的rpc接口,以及pojo传输对象</br>
       --service-impl:主要实现rpc接口,以及整合mybatis,利用mybatis插件生成对应的mapper以及pojo对象,以及可以直接利用maven插件将此包打成tar包,解压直接在linux下运行</br>
 * c:web-api主要是以http形式提供给第三方调用,此模块依赖service-api,commons模块</br>
       --config包,主要定义此web需要的配置,例如初始化commons模块下的jedis客户端,新增拦截器等等,全部以注解的形式配置</br>
       --exception包,主要是定义自己的业务异常，以及利用spring4的@RestControllerAdvice统一处理系统异常</br>
       --properties包,主要定义系统一些属性文件对应的实体类</br>
  
  
 

  
  

  
  
