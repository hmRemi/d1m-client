package com.d1m.client.management.cosmetics;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;

public class CosmeticModelBase extends ModelBase{

    protected final ModelBiped playerModel;

    public CosmeticModelBase(RenderPlayer player) {
        this.playerModel = player.getMainModel();
    }
}
