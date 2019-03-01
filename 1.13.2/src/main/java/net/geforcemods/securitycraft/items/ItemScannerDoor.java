package net.geforcemods.securitycraft.items;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemScannerDoor extends Item
{
	public ItemScannerDoor()
	{
		super(new Item.Properties().group(SecurityCraft.groupSCDecoration));
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext ctx)
	{
		return onItemUse(ctx.getPlayer(), ctx.getWorld(), ctx.getPos(), ctx.getItem(), ctx.getFace(), ctx.getHitX(), ctx.getHitY(), ctx.getHitZ(), ctx);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumFacing facing, float hitX, float hitY, float hitZ, ItemUseContext ctx)
	{
		if(world.isRemote)
			return EnumActionResult.FAIL;

		if (facing != EnumFacing.UP)
			return EnumActionResult.FAIL;
		else
		{
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if (!block.isReplaceable(world.getBlockState(pos), new BlockItemUseContext(ctx)))
				pos = pos.offset(facing);

			if (player.canPlayerEdit(pos, facing, stack) && SCContent.scannerDoor.isValidPosition(world.getBlockState(pos), world, pos))
			{
				EnumFacing angleFacing = EnumFacing.fromAngle(player.rotationYaw);
				int offsetX = angleFacing.getXOffset();
				int offsetZ = angleFacing.getZOffset();
				boolean flag = offsetX < 0 && hitZ < 0.5F || offsetX > 0 && hitZ > 0.5F || offsetZ < 0 && hitX > 0.5F || offsetZ > 0 && hitX < 0.5F;
				placeDoor(world, pos, angleFacing, SCContent.scannerDoor, flag);
				SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				stack.shrink(1);

				if(world.getTileEntity(pos) != null)
				{
					((TileEntityOwnable) world.getTileEntity(pos)).getOwner().set(player.getGameProfile().getId().toString(), player.getName());
					((TileEntityOwnable) world.getTileEntity(pos.up())).getOwner().set(player.getGameProfile().getId().toString(), player.getName());
				}

				return EnumActionResult.SUCCESS;
			}
			else
				return EnumActionResult.FAIL;
		}
	}

	public static void placeDoor(World world, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) //naming might not be entirely correct, but it's giving a rough idea
	{
		BlockPos left = pos.offset(facing.rotateY());
		BlockPos right = pos.offset(facing.rotateYCCW());
		int rightNormalCubeAmount = (world.getBlockState(right).isNormalCube() ? 1 : 0) + (world.getBlockState(right.up()).isNormalCube() ? 1 : 0);
		int leftNormalCubeAmount = (world.getBlockState(left).isNormalCube() ? 1 : 0) + (world.getBlockState(left.up()).isNormalCube() ? 1 : 0);
		boolean isRightDoor = world.getBlockState(right).getBlock() == door || world.getBlockState(right.up()).getBlock() == door;
		boolean isLeftDoor = world.getBlockState(left).getBlock() == door || world.getBlockState(left.up()).getBlock() == door;

		if ((!isRightDoor || isLeftDoor) && leftNormalCubeAmount <= rightNormalCubeAmount)
		{
			if (isLeftDoor && !isRightDoor || leftNormalCubeAmount < rightNormalCubeAmount)
				isRightHinge = false;
		}
		else
			isRightHinge = true;

		BlockPos blockAbove = pos.up();
		boolean isAnyPowered = world.isBlockPowered(pos) || world.isBlockPowered(blockAbove);
		IBlockState state = door.getDefaultState().with(BlockDoor.FACING, facing).with(BlockDoor.HINGE, isRightHinge ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT).with(BlockDoor.POWERED, Boolean.valueOf(isAnyPowered)).with(BlockDoor.OPEN, Boolean.valueOf(isAnyPowered));
		world.setBlockState(pos, state.with(BlockDoor.HALF, DoubleBlockHalf.LOWER), 2);
		world.setBlockState(blockAbove, state.with(BlockDoor.HALF, DoubleBlockHalf.UPPER), 2);
		world.notifyNeighborsOfStateChange(pos, door);
		world.notifyNeighborsOfStateChange(blockAbove, door);
	}
}