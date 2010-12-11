class GroupsController < ApplicationController
  filter_resource_access

  def index
    @groups = Group.all(:include => :contacts)
  end

  def new
    @group = Group.new
  end

  def create
    @group = Group.new(params[:group])
    if @group.save
      flash[:notice] = 'Group created successfully'
      redirect_to groups_path
    else
      flash[:alert] = 'Group creating failed'
      render 'new'
    end
  end
end