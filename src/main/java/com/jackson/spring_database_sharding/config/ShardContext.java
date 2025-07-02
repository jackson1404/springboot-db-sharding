/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.config;

/**
 * ShardContext Class.
 * <p>
 * </p>
 *
 * @author
 */

public class ShardContext {

    private static final ThreadLocal<String> CURRENT_THREAD = new ThreadLocal<>();

    public static void setCurrentThread(String shard){
        System.out.println("the shard" + shard);
        CURRENT_THREAD.set(shard);
    }

    public static String getCurrentThread(){
        return CURRENT_THREAD.get();
    }

    public static void removeCurrentThread(){
        CURRENT_THREAD.remove();
    }



}
