import java.util.Scanner;

/**
 * Created by admin on 2017/7/31.
 * 商品管理类
 */
public class GoodsManager {

    Goods[] goods = new Goods[7];
    String[] names = new String[]{"addidas运动鞋", "Kappa网球裙", "网球拍", "addidasT恤", "Nike运动鞋", "Kappa网球", "KappaT恤"};
    float[] prices = new float[]{800, 550, 480, 320.5f, 900, 100, 262.3f};
    SelectedGoods[] selectedGoodsArray;
    int selectedGoodsIndex = 0;

    public GoodsManager() {
        for (int i = 0; i < goods.length; i++) {
            goods[i] = new Goods(names[i], prices[i]);
        }
    }

    // 根据索引获取商品
    public Goods getGoodsByIndex(int index) {
        if (index >= 0 && index < goods.length) {
            return goods[index];
        }
        return null;
    }

    // 开始选购商品
    public void startSelectGoods() {
        selectedGoodsIndex = 0;
        selectedGoodsArray = new SelectedGoods[7];
    }

    // 添加一个已选购的商品
    public void addSelectedGoods(SelectedGoods selectedGoods) {
        if (selectedGoodsIndex >= selectedGoodsArray.length) {
            System.out.println("不能选择更多商品了");
        } else {
            selectedGoodsArray[selectedGoodsIndex++] = selectedGoods;
        }
    }

    // 显示已选购的商品并结算，返回值是获得的积分
    public int showSelectedGoods() {
        float totalPrice = 0;
        System.out.println("*************************消费清单************************\n");
        System.out.println("物品\t\t\t\t单价\t\t\t个数\t\t\t金额");
        for (SelectedGoods item : selectedGoodsArray) {
            if (item != null) {
                totalPrice += item.goods.price * item.count;
                System.out.println(item.goods.name + "\t\t" + item.goods.price + "\t\t" + item.count + "\t\t" + item.goods.price * item.count);
            }
        }
        float discount = getDiscount(totalPrice);
        System.out.println("折扣：" + discount);
        totalPrice *= discount;
        System.out.println("金额总计：" + totalPrice);
        System.out.println("实际消费：");
        Scanner scanner = Main.getScanner();
        float payMoney = scanner.nextFloat();
        System.out.println("找钱：" + (payMoney - totalPrice));
        int getPoints = (int)(totalPrice / 10);
        System.out.println("本次购物获得的积分是：" + getPoints);
        return getPoints;
    }

    // 获取折扣
    private float getDiscount(float totalPrice) {
        float discount = 0;
        if (totalPrice > 5000) {
            discount = 0.75f;
        } else if (totalPrice > 4000 && totalPrice <= 5000) {
            discount = 0.8f;
        } else if (totalPrice > 3000 && totalPrice <= 4000) {
            discount = 0.85f;
        } else if (totalPrice > 2000 && totalPrice <= 3000) {
            discount = 0.9f;
        } else if (totalPrice > 1000 && totalPrice <= 2000) {
            discount = 0.95f;
        }
        return discount;
    }

}
