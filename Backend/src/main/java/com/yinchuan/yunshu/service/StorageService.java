package com.yinchuan.yunshu.service;

import com.yinchuan.yunshu.config.Storage;
import com.yinchuan.yunshu.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Method for storage
 */
@Service
public class StorageService {
    /**
     * Interface for connecting Storage Repository
     */
    @Autowired
    private StorageRepository goods;

    /**
     * Get all goods of a specific user.
     *
     * @return The List of goods from the StorageRepository method findByUserId
     */
    public List<Storage> findByUserId(int userId){return goods.findByUserId(userId, Sort.by("type"));}

    /**
     * Get a specific goods of a specific user.
     *
     * @return The List of goods from the StorageRepository method findByUserId
     */
    public Storage findByIdAndUserId(int id, int userId){return goods.findByIdAndUserId(id, userId );}

    /**
     * Get all goods of a same type.
     *
     * @return The list of goods from the StorageRepository method findByType.
     */
    public List<Storage> findByType(String type){return goods.findByType(type);}

    /**
     * Get all goods in all storage
     */
    public List<Storage> findAll(){return goods.findAll();}

    /**
     * Add a new goods into the storage.
     * @param good The good you want to add into the storage.
     */
    public String addGood(Storage good){
        this.goods.save(good);
        return good.getWeight()+"Kg. "+good.getType()+"has been added to inventory";
    }

    /**
     * Delete the good that sold out
     * @param id
     * @param userId
     */


    /**
     * Edit a good's description for a given user
     */
    public String editDescription(int goodId, int userId, String description){
        Storage good = goods.findByIdAndUserId(goodId, userId);
        good.editDescription(description);
        this.goods.save(good);
        return "商品： "+good.getId()+good.getType()+"的描述已更新";
    }

}
