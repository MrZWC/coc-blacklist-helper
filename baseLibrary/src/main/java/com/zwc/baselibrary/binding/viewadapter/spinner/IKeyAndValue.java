package com.zwc.baselibrary.binding.viewadapter.spinner;

/**
 * ClassName IKeyAndValue
 * User: zuoweichen
 * Date: 2021/4/19 11:51
 * Description: 下拉Spinner控件的键值对, 实现该接口,返回key,value值, 在xml绑定List<IKeyAndValue>
 */
public interface IKeyAndValue {
    String getKey();

    String getValue();
}
