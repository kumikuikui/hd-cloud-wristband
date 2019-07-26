package com.coins.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: WarrantyUserBo
  * @Description: 保单用户
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年7月24日 下午3:16:14
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyUser {
	
	private int id;
	private String casetype;
	private String memberid;
	private String inpname;
	private String inpnric;
	private String inpdob;
	private String inpsex;
	private String inpnat;
	private String inprace;
	private int inphgt;
	private int inpwgt;
	private String inpjob;
	private String natureofwork;
	private String add001;
	private String add002;
	private String add003;
	private String postcode;
	private String telhome;
	private String hpno;
	private String bplan;
	private int loading;
	private String payfreq;
	private String submissiondate;
	private String prodate;
	private String inceptiondate;
	private int policystatus;

}
