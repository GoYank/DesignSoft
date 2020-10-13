package com.example.designsoft.build;

import android.util.Log;

/**
 * author : Gyk
 * time   : 2020/9/28
 * todo   :
 */
public class ComputerDesign {
    private String Cpu;
    private String Gpu;
    private String HardDisk;
    private String Power;


    public ComputerDesign(Builder builder) {
        this.Cpu = builder.Cpu;
        this.Gpu = builder.Gpu;
        this.HardDisk = builder.HardDisk;
        this.Power = builder.Power;
    }


    public void show() {
        Log.i("DesignSoft", "Cpu--->" + this.Cpu + "\t" + "Gpu--->" + this.Gpu + "\t" + "HardDisk--->" + this.HardDisk + "\t" + "Power--->" + this.Power);
    }

    public static class Builder {
        private String Cpu;
        private String Gpu;
        private String HardDisk;
        private String Power;

        public Builder withCpu(String Cpu) {
            this.Cpu = Cpu;
            return this;
        }

        public Builder withGpu(String Gpu) {
            this.Gpu = Gpu;
            return this;
        }

        public Builder withHardDisk(String HardDisk) {
            this.HardDisk = HardDisk;
            return this;
        }

        public Builder withPower(String Power) {
            this.Power = Power;
            return this;
        }

        public ComputerDesign build() {
            return new ComputerDesign(this);
        }
    }
}
