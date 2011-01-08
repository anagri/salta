class AndroidController < ApplicationController
  GROUP_FIELDS   = [:id, :name]
  CONTACT_FIELDS = [:id, :first_name, :last_name, :phone, :email]

  def groups
    render :json => current_user.groups.to_json(:only => GROUP_FIELDS)
  end

  def contacts
    group = current_user.groups.find(params[:group_id])
    render :json => group.contacts.to_json(:only => CONTACT_FIELDS)
  end
end