class Group < ActiveRecord::Base
  has_many :memberships
  has_many :contacts, :through => :memberships
  has_many :invites

end