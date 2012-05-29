package core;

public class tileProperties
{
	private boolean checkedHorizontal;
	private boolean checkedVertical;
	private boolean scoredHorizontal;
	private boolean scoredVertical;

	public tileProperties()
	{
		checkedHorizontal = false;
		checkedVertical = false;
		scoredHorizontal = false;
		scoredVertical = false;
	}

	public boolean isScoredHorizontal()
	{
		return scoredHorizontal;
	}
	
	public void setScoredHorizontal(boolean scoredHorizontal)
	{
		this.scoredHorizontal = scoredHorizontal;
	}
	
	public boolean isCheckedHorizontal()
	{
		return checkedHorizontal;
	}
	
	public void setCheckedHorizontal(boolean checkedHorizontal)
	{
		this.checkedHorizontal = checkedHorizontal;
	}
	
	public boolean isScoredVertical()
	{
		return scoredVertical;
	}
	
	public void setScoredVertical(boolean scoredVertical)
	{
		this.scoredVertical = scoredVertical;
	}
	
	public boolean isCheckedVertical()
	{
		return checkedVertical;
	}
	
	public void setCheckedVertical(boolean checkedVertical)
	{
		this.checkedVertical = checkedVertical;
	}
}